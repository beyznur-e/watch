const apiBaseUrl = "http://localhost:8080/api/auth"; // Backend URL'si

// JWT Token'dan rol bilgisi alma fonksiyonu
function getRoleFromToken() {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
        const payload = JSON.parse(atob(token.split('.')[1])); // Token'ın payload kısmını decode et
        console.log("Token Payload:", payload); // Debug için kontrol et

        let role = payload.role || payload.authorities?.[0] || null;
        
        // Eğer rol "ROLE_ROLE_ADMIN" gibi bir hata içeriyorsa düzeltiliyor
        if (role?.startsWith("ROLE_ROLE_")) {
            role = role.replace("ROLE_ROLE_", ""); // "ROLE_ROLE_ADMIN" -> "ADMIN"
        } else if (role?.startsWith("ROLE_")) {
            role = role.replace("ROLE_", ""); // "ROLE_ADMIN" -> "ADMIN"
        }

        return role;
    } catch (error) {
        console.error("Token çözümleme hatası:", error);
        return null;
    }
}



// Kullanıcı giriş yaptıysa sayfa yüklenirken gerekli butonları göster/gizle
window.addEventListener("DOMContentLoaded", function () {
    const adminPanelBtn = document.getElementById("admin-panel");
    const adminLink = document.getElementById("admin-link");

    const role = getRoleFromToken();

    if (role === "ADMIN") {
        if (adminPanelBtn) adminPanelBtn.style.display = "block";
        if (adminLink) adminLink.style.display = "block";
    } else {
        if (adminPanelBtn) adminPanelBtn.style.display = "none";
        if (adminLink) adminLink.style.display = "none";
    }

    // Giriş/Kayıt formu geçiş işlemleri
    const switchToRegister = document.getElementById("switchToRegister");
    const switchToLogin = document.getElementById("switchToLogin");
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");
    const formTitle = document.getElementById("formTitle");

    if (switchToRegister) {
        switchToRegister.addEventListener("click", function (e) {
            e.preventDefault();
            loginForm.style.display = "none";
            registerForm.style.display = "block";
            formTitle.innerText = "Kayıt Ol";
        });
    }

    if (switchToLogin) {
        switchToLogin.addEventListener("click", function (e) {
            e.preventDefault();
            loginForm.style.display = "block";
            registerForm.style.display = "none";
            formTitle.innerText = "Giriş Yap";
        });
    }
});

// Giriş işlemi
if (document.getElementById("loginForm")) {
    document.getElementById("loginForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("login-username").value;
        const password = document.getElementById("login-password").value;

        try {
            const response = await fetch(`${apiBaseUrl}/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "Giriş başarısız!");
            }

            const token = await response.text();
            if (!token.startsWith("eyJ")) throw new Error("Geçersiz token!");

            localStorage.setItem("token", token);
            alert("Giriş başarılı!");

            const role = getRoleFromToken();
            console.log("Kullanıcı Rolü:", role); // Debug için

            if (role === "ADMIN") {
                window.location.href = "admin.html";
            } else if (role === "USER") {
                window.location.href = "dashboard.html";
            } else {
                throw new Error("Bilinmeyen rol!");
            }
        } catch (error) {
            console.error("Giriş hatası:", error);
            alert(error.message);
        }
    });
}

// Kayıt işlemi
if (document.getElementById("registerForm")) {
    document.getElementById("registerForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("register-username").value;
        const password = document.getElementById("register-password").value;
        const confirmPassword = document.getElementById("register-confirmPassword").value;

        if (password !== confirmPassword) {
            alert("Şifreler uyuşmuyor.");
            return;
        }

        try {
            const response = await fetch(`${apiBaseUrl}/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "Kayıt başarısız!");
            }

            alert("Kayıt başarılı! Giriş yapabilirsiniz.");
            window.location.href = "index.html";
        } catch (error) {
            console.error("Kayıt hatası:", error);
            alert(error.message);
        }
    });
    // Admin paneline yetkisiz erişimi engelleme
    if (window.location.pathname.includes("admin.html")) {
        window.addEventListener("DOMContentLoaded", function () {
            const role = getRoleFromToken();
            if (role !== "ADMIN") {
                alert("Yetkisiz erişim! Ana sayfaya yönlendiriliyorsunuz.");
                window.location.href = "index.html";
            }
        });
    }
}


