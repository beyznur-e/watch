# Watch Projesi

## Proje Hakkında
Watch, içerik izleme platformu olarak tasarlanmış, kimlik doğrulama ve yetkilendirme işlevlerini içeren Spring Boot tabanlı bir projedir. Bu proje, kullanıcıların içeriklere güvenli bir şekilde erişmesini, filtrelemesini ve izleme listesine eklemesini sağlamayı amaçlamaktadır.

## Özellikler
- Kullanıcı kimlik doğrulama (Giriş/Kayıt)
- Rol tabanlı yetkilendirme
- Kullanıcılar için içerik listeleme ve filtreleme
- Kullanıcılar izleme listesine içerik ekleyebilir
- Admin paneli üzerinden içerik yönetimi (Ekleme, Güncelleme, Silme)
- Güvenli API uç noktaları
- Basit frontend uygulaması

## Kullanılan Teknolojiler
- **Backend:** Java, Spring Boot, Spring Security, JPA, Hibernate
- **Veritabanı:** PostgreSQL 

## Kurulum & Başlatma
### Gereksinimler
- Java 17+
- Maven
- PostgreSQL

### Çalıştırma Adımları
1. Depoyu klonlayın:
   ```sh
   git clone https://github.com/beyznur-e/watch.git
   cd watch
   ```
2. `application.properties` dosyasında veritabanı yapılandırmasını ayarlayın:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/watch
   spring.datasource.username=postgres
   spring.datasource.password=1234
   ```
3. Uygulamayı derleyin ve çalıştırın:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Uç Noktaları
| Yöntem | Uç Nokta                      | Açıklama |
|--------|------------------------------|------------------------------|
| POST   | /api/auth/login              | Kullanıcı girişi            |
| POST   | /api/auth/register           | Kullanıcı kaydı             |
| GET    | /api/content                 | İçerik verilerini getir     |
| POST   | /api/admin/content/add       | Admin platforma içerik ekler |
| PUT    | /api/admin/content/update    | Admin içeriği günceller      |
| DELETE | /api/admin/content/delete    | Admin içeriği siler          |
| GET    | /api/admin/user/list         | Admin kullanıcıları listeler |
| DELETE | /api/admin/user/delete       | Admin kullanıcı siler        |
| GET    | /api/contents/list           | İçerikleri listeler         |
| GET    | /api/contents/byType         | Türe göre içerik listeler   |
| GET    | /api/contents/byGenre        | Türe göre içerik listeler   |
| POST   | /api/user-content/save       | İçeriği kullanıcıya kaydeder |
| DELETE | /api/user-content/delete     | İçeriği kullanıcıdan siler   |
| GET    | /api/user-content/watchlist  | Kullanıcının içeriklerini listeler |
| PUT    | /api/user/update             | Kullanıcı bilgilerini günceller |
| DELETE | /api/user/delete             | Kullanıcıyı siler           |
| POST   | /api/user/addwatchlist       | İzleme listesine içerik ekler |
| GET    | /api/user/watchlist          | Kullanıcının izleme listesini getirir |

## Katkıda Bulunma
Eğer katkıda bulunmak isterseniz, repoyu fork'layarak bir pull request oluşturabilirsiniz!

---
Projede yapılan yeni eklemelere veya değişikliklere göre bu dosyayı güncelleyebilirsiniz!

