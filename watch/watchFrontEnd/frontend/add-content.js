// Token kontrol fonksiyonu
function checkToken() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Giriş yapmanız gerekiyor.");
        window.location.href = "index.html"; // Giriş sayfasına yönlendir
        return null; // Token yoksa işlem yapma
    }
    return token;
}

document.getElementById("add-content-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Formun varsayılan submit davranışını engelliyoruz

    const token = checkToken(); // Token kontrolünü burada çağırıyoruz
    if (!token) return;

    const contentData = {
        title: document.getElementById("title").value,
        type: document.getElementById("type").value,
        year: document.getElementById("year").value,
        genre: document.getElementById("genre").value
    };

    // Eğer kullanıcı bilgileri eksikse, işlem yapma
    if (!contentData.title || !contentData.type || !contentData.year || !contentData.genre) {
        alert("Tüm alanları doldurduğunuzdan emin olun.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/admin/content/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(contentData)
        });

        if (response.ok) {
            alert("İçerik başarıyla eklendi!");
            window.location.href = "dashboard.html"; // Başarıyla ekledikten sonra dashboard'a yönlendir
        } else {
            const errorData = await response.json();
            alert(`İçerik eklenemedi: ${errorData.message || "Bilinmeyen hata."}`);
        }
    } catch (error) {
        console.error("Hata oluştu:", error);
        alert("Bir hata oluştu, tekrar deneyin.");
    }
});
