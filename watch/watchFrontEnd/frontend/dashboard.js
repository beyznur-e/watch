// Çıkış yapma fonksiyonu
function logout() {
    if (confirm("Çıkış yapmak istediğinizden emin misiniz?")) {
        // localStorage'dan token'ı sil
        localStorage.removeItem("token");

        // Kullanıcıyı giriş sayfasına yönlendir
        window.location.href = "index.html";
    }
}

// Filtreleme butonuna tıklama işlemi
function applyFilters() {
    const type = document.getElementById("filter-type").value;
    const genre = document.getElementById("filter-genre").value;

    // Boş filtre durumu
    if (!type && !genre) {
        alert("Lütfen bir filtre seçin.");
        return;
    }

    // Filtreleme işlemi
    const url = buildUrl(type, genre);
    fetchContents(url);
}

// URL oluşturma fonksiyonu
function buildUrl(type = '', genre = '') {
    let url = "http://localhost:8080/api/contents/list"; // Varsayılan URL

    if (type && genre) {
        url = `http://localhost:8080/api/contents/list?type=${type}&genre=${genre}`;
    } else if (type) {
        url = `http://localhost:8080/api/contents/byType?type=${type}`;
    } else if (genre) {
        url = `http://localhost:8080/api/contents/byGenre?genre=${genre}`;
    }

    return url;
}

// İçerikleri API'den çekme fonksiyonu
async function fetchContents(url) {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const headers = new Headers();
    headers.append("Authorization", "Bearer " + token);
    headers.append("Content-Type", "application/json");

    try {
        const response = await fetch(url, { headers });

        if (response.ok) {
            const contents = await response.json();
            displayContents(contents);
        } else {
            const errorData = await response.json();
            console.error("API Error:", errorData);
            alert(`Bir hata oluştu: ${errorData.message || 'Bilinmeyen hata'}`);
        }
    } catch (error) {
        console.error("Network Error:", error);
        alert("Bir ağ hatası oluştu. Lütfen tekrar deneyin.");
    }
}

// İçerikleri ekrana basma fonksiyonu
function displayContents(contents) {
    const contentList = document.getElementById("content-list");
    contentList.innerHTML = ""; // Listeyi temizle

    if (contents.length === 0) {
        contentList.innerHTML = "<p>Filtreye uygun içerik bulunamadı.</p>";
        return;
    }

    contents.forEach(content => {
        const contentItem = document.createElement("div");
        contentItem.classList.add("content-item");

        const img = document.createElement("img");
        img.src = content.imageUrl || "default-image.jpg"; // Varsayılan resim

        const contentInfo = document.createElement("div");
        contentInfo.classList.add("content-info");
        contentInfo.innerText = `${content.title || "Bilinmeyen Başlık"} (${content.year || "Bilinmeyen Yıl"})`;

        const title = document.createElement("h4");
        title.innerText = content.title || "Bilinmeyen Başlık";

        contentItem.appendChild(img);
        contentItem.appendChild(contentInfo);
        contentItem.appendChild(title);
        contentList.appendChild(contentItem);
    });
}
