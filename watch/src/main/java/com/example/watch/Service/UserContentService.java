package com.example.watch.Service;

import com.example.watch.Entity.UserContent;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Repository.UserContentRepository;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.Interface.UserContentServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UserContentService implements UserContentServiceI {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final UserContentRepository userContentRepository;

    public UserContentService(UserRepository userRepository, ContentRepository contentRepository, UserContentRepository userContentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.userContentRepository = userContentRepository;
    }

    // Kullanıcı ve içerik ilişkisinin kaydedilmesi
    @Override
    public void saveContentForUser(Long user_id, Long content_id) {
        UserContent userContent = new UserContent();
        userContent.setUserId(user_id);
        userContent.setContent_id(content_id);
        userContentRepository.save(userContent);
    }


    // İzleme listesinden içerik kaldırma işlemi
    @Override
    public void removeContentFromWatchList(Long user_id, Long content_id) {
        Optional<UserContent> userContent = userContentRepository.findByUserIdAndContentId(user_id, content_id);

        if (userContent.isPresent()) {
            userContentRepository.delete(userContent.get());
        } else {
            throw new EntityNotFoundException("İlgili içerik bulunamadı.");
        }
    }


    //İzleme listesini listeleme
    @Override
    public List<UserContent> findByUserId(Long user_id) {
        return userContentRepository.findByUserId(user_id);
    }
}
