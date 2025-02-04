const apiBaseUrl = "http://localhost:8080/api"; // API base URL'ini buraya ekle

// Kullanıcı çıkış yapma
function logout() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}

// Token'dan kullanıcı ID'sini almak
async function getUserIdFromToken() {
    const token = localStorage.getItem("token");
    if (!token) {
        console.error("❌ Token bulunamadı!");
        alert("Giriş yapmanız gerekiyor.");
        window.location.href = "index.html";
        return null;
    }

    try {
        // Token'i çözüp kullanıcı kimliğini al
        const payload = JSON.parse(atob(token.split('.')[1])); 
        console.log("Decoded Token Payload:", payload);

        // Token süresi dolmuş mu kontrol et
        const now = Math.floor(Date.now() / 1000); // Şu anki zaman (saniye cinsinden)
        if (payload.exp && payload.exp < now) {
            console.error("❌ Token süresi dolmuş!");
            alert("Oturumunuz süresi doldu, lütfen tekrar giriş yapın.");
            localStorage.removeItem("token");
            window.location.href = "index.html";
            return null;
        }

        const userId = payload.userId;
        if (userId) {
            return userId;  // Eğer token içinde varsa, doğrudan dön
        }

        // Eğer userId token içinde yoksa API'den al
        const response = await fetch(`${apiBaseUrl}/user/me`, {
            method: "GET",
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) throw new Error("Kullanıcı bilgisi alınamadı!");

        const userData = await response.json();
        return userData.id;  
    } catch (error) {
        console.error("❌ Kullanıcı kimliği alınamadı!", error);
        alert("Oturumunuz geçersiz, tekrar giriş yapın.");
        localStorage.removeItem("token");
        window.location.href = "index.html";
        return null;
    }
}


// İzleme listesine içerik ekleme
async function addToWatchlist(contentId) {
    const token = localStorage.getItem("token");
    const userId = await getUserIdFromToken();  
    if (!userId) {
        console.error("❌ Kullanıcı kimliği alınamadı!");
        return;
    }

    console.log(`📡 API isteği gönderiliyor: userId=${userId}, contentId=${contentId}`);

    const requestBody = JSON.stringify({ userId, contentId });

    try {
        const response = await fetch(`${apiBaseUrl}/user/watchlist/add`, {  // Endpoint'i düzelt
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"  // JSON gönderiyoruz
            },
            body: requestBody
        });

        if (response.status === 401 || response.status === 403) {
            console.error("❌ Token geçersiz ya da yetkiniz yok.");
            alert("Oturumunuz geçersiz. Lütfen giriş yapın.");
            window.location.href = "index.html";
            return;
        }

        if (!response.ok) {
            throw new Error(`Hata: ${response.status}`);
        }

        alert("✅ İçerik başarıyla izleme listesine eklendi!");
        loadWatchlist();  // Listeyi güncelle
    } catch (error) {
        console.error("❌ İçerik eklenirken hata oluştu:", error);
        alert("İçerik eklenirken hata oluştu: " + error.message);
    }
}


// İzleme listesini yüklemek
async function loadWatchlist() {
    const token = localStorage.getItem("token");
    const userId = await getUserIdFromToken();  // Burada await kullanmak önemli
    if (!userId) return;  // Eğer userId alınamazsa işlemi sonlandır

    try {
        const response = await fetch(`${apiBaseUrl}/user/watchlist?userId=${userId}`, {
            method: "GET",
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) throw new Error(`Sunucu hatası: ${response.status}`);

        const watchlist = await response.json();
        const watchlistContainer = document.getElementById("watchlist");
        watchlistContainer.innerHTML = ""; // Listeyi temizle

        watchlist.forEach(item => {
            const li = document.createElement("li");
            li.textContent = `${item.title} (${item.year})`; // Doğru alanları kullan
            watchlistContainer.appendChild(li);
        });
    } catch (error) {
        console.error("İzleme listesi yüklenirken hata oluştu:", error);
        alert("İzleme listesi yüklenemedi.");
    }
}


// İçerik listesi yüklenmesi
async function loadContentList() {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch(`${apiBaseUrl}/contents/list`, {
            method: "GET",
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) throw new Error(`Sunucu hatası: ${response.status}`);

        const contentList = await response.json();
        const contentContainer = document.getElementById("content-list");
        contentContainer.innerHTML = "";

        contentList.forEach(content => {
            if (!content.id) {
                console.error("Eksik içerik verisi:", content);
                return; // Eğer content.id yoksa işlemi iptal et
            }

            const li = document.createElement("li");
            li.textContent = `${content.title} (${content.year})`;

            const addButton = document.createElement("button");
            addButton.textContent = "Ekle";
            addButton.addEventListener("click", async () => {
                console.log(`Ekle butonuna basıldı: İçerik ID ${content.id}`);
                await addToWatchlist(content.id);
            });

            li.appendChild(addButton);
            contentContainer.appendChild(li);
        });
    } catch (error) {
        console.error("İçerik listesi yüklenirken hata oluştu:", error);
        alert("İçerik listesi yüklenemedi.");
    }
}

// Kullanıcı listesini yüklemek
async function listUsers() {
    const token = localStorage.getItem("token");
    if (!token) {
        console.error("❌ Token bulunamadı!");
        alert("Giriş yapmanız gerekiyor.");
        window.location.href = "index.html";
        return;
    }

    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const userRole = payload.role;  // Token'dan kullanıcı rolünü al

        // Kullanıcı rolünü kontrol et
        if (userRole !== 'ADMIN') {
            console.log("❌ Admin olmayan kullanıcı, kullanıcı listesine erişemez.");
            alert("Bu işlemi sadece admin yapabilir.");
            return;
        }

        const userId = payload.userId;  // Token'dan userId al

        // Kullanıcı listesi API isteği
        const response = await fetch(`${apiBaseUrl}/user/list?userId=${userId}`, {
            method: "GET",
            headers: { "Authorization": "Bearer " + token }
        });

        if (response.status === 403) {
            alert("Bu işlemi yapmaya yetkiniz yok.");
            return;
        }

        if (!response.ok) throw new Error(`Sunucu hatası: ${response.status}`);

        const users = await response.json();
        console.log("Kullanıcılar:", users);

    } catch (error) {
        console.error("❌ Kullanıcılar yüklenirken hata oluştu:", error);
        alert("Kullanıcılar yüklenemedi.");
    }
}

// Sayfa yüklendiğinde içerikleri ve izleme listesini yükle
document.addEventListener("DOMContentLoaded", function () {
    loadWatchlist();
    loadContentList();

    setTimeout(() => {
        console.log("Sayfadaki butonlar:", document.querySelectorAll("button"));
    }, 2000);
});
