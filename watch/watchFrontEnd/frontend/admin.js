const apiBaseUrl = "http://localhost:8080/api/admin";  // Admin API'si
const contentApiUrl = "http://localhost:8080/api/contents/list";  // İçerik listeleme API'si

// İçerik ekleme formu
document.getElementById("addContentForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const title = document.getElementById("title").value;
    const genre = document.getElementById("genre").value;
    const type = document.getElementById("type").value;

    try {
        const response = await fetch(`${apiBaseUrl}/content/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token"), // JWT token eklemeyi unutma
            },
            body: JSON.stringify({ title, genre, type }),
        });

        if (!response.ok) {
            throw new Error("İçerik eklenemedi!");
        }

        alert("İçerik başarıyla eklendi!");
        loadContents();  // İçerikleri tekrar yükle
    } catch (error) {
        console.error("İçerik ekleme hatası:", error);
        alert(error.message);
    }
});

// İçerikleri yüklemek için API çağrısı
async function loadContents() {
    try {
        // API'den içerikleri alıyoruz
        const response = await fetch('http://localhost:8080/api/contents/list', {  // Tam URL kullanıyoruz
            method: 'GET',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"), // Kullanıcı token'ını kullanıyoruz
            }
        });

        if (!response.ok) {
            throw new Error('İçerikler getirilemedi!');
        }

        const contents = await response.json();
        const contentListElement = document.getElementById("contentItems");

        contentListElement.innerHTML = ""; // Listeyi temizle

        if (contents.length === 0) {
            contentListElement.innerHTML = "Henüz içerik yok.";
            return;
        }

        // Gelen içerikleri listeye ekle
        contents.forEach(content => {
            const li = document.createElement("li");
            li.textContent = `${content.title} - ${content.genre} - ${content.type}`;

            // Silme butonu
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Sil";
            deleteButton.addEventListener("click", () => deleteContent(content.id));

            // Güncelleme butonu
            const updateButton = document.createElement("button");
            updateButton.textContent = "Güncelle";
            updateButton.addEventListener("click", () => updateContent(content.id));

            li.appendChild(deleteButton);
            li.appendChild(updateButton);
            contentListElement.appendChild(li);
        });
    } catch (error) {
        console.error("Hata:", error);
        alert(error.message);  // Hata mesajını göster
    }
}

// Sayfa yüklendiğinde içerikleri yükle
window.onload = function () {
    loadContents();
};

// İçerik silme fonksiyonu
async function deleteContent(contentId) {
    try {
        const response = await fetch(`${apiBaseUrl}/content/delete?contentId=${contentId}`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
            },
        });

        if (!response.ok) {
            throw new Error("İçerik silinemedi!");
        }

        alert("İçerik başarıyla silindi.");
        loadContents();  // Silme sonrası listeyi güncelle
    } catch (error) {
        console.error("İçerik silinemedi:", error);
        alert(error.message);
    }
}

// İçerik güncelleme fonksiyonu
async function updateContent(contentId) {
    const title = prompt("Yeni içerik başlığı:");
    const genre = prompt("Yeni içerik türü:");
    const type = prompt("Yeni içerik tipi:");

    if (!title || !genre || !type) {
        alert("Tüm alanları doldurun!");
        return;
    }

    const contentDto = { title, genre, type };

    try {
        const response = await fetch(`${apiBaseUrl}/content/update?contentId=${contentId}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token"),
            },
            body: JSON.stringify(contentDto),
        });

        if (!response.ok) {
            throw new Error("İçerik güncellenemedi!");
        }

        alert("İçerik başarıyla güncellendi.");
        loadContents();  // Güncelleme sonrası listeyi güncelle
    } catch (error) {
        console.error("İçerik güncellenemedi:", error);
        alert(error.message);
    }
}
