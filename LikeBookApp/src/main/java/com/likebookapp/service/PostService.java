package com.likebookapp.service;

import com.likebookapp.model.dtos.MyPostDTO;
import com.likebookapp.model.dtos.OtherPostDTO;
import com.likebookapp.model.dtos.PostCreatedDto;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodName;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.MoodRepository;
import com.likebookapp.repository.PostRepository;
import com.likebookapp.repository.UserRepository;
import com.likebookapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final MoodRepository moodRepository;
    private final LoggedUser userSession;
    private final PostRepository postRepository;

    @Autowired
    public PostService(UserRepository userRepository,
                       MoodRepository moodRepository,
                       LoggedUser userSession,
                       PostRepository postRepository) {
        this.userRepository = userRepository;
        this.moodRepository = moodRepository;
        this.userSession = userSession;
        this.postRepository = postRepository;
    }
    public void created(PostCreatedDto postCreatedDto) {
        MoodName moodName = MoodName.valueOf(postCreatedDto.getMood());
        Mood mood = this.moodRepository.findByMoodName(moodName);

        Optional<User> creator = this.userRepository.findById(this.userSession.getId());

        Post post = new Post()
                .setContent(postCreatedDto.getContent())
                .setMood(mood)
                .setCreator(creator.get());

        this.postRepository.save(post);
    }

    public List<MyPostDTO> getPostWrittenBy(long loggedUserId) {
        return this.postRepository.findByCreatorId(loggedUserId)
                .stream()
                .map(MyPostDTO::new)
                .collect(Collectors.toList());
    }

    public List<OtherPostDTO> getPostNotWrittenBy(long loggedUserId) {
        return this.postRepository.findByCreatorIdNot(loggedUserId)
                .stream()
                .map(OtherPostDTO::new)
                .collect(Collectors.toList());
    }

    public void likePostWithId(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Objects.requireNonNull(post).getUserLikes().add(user);

        postRepository.save(post);
    }

    public void removePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
