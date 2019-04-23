package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordBookController {

    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private UsersRepo usersRepo;

    //@Transactional
    @PostMapping(path = "save_word")
    @ResponseBody
    public WordRecord saveWord(@RequestBody WordRecord wordRecord) {
        wordRecord.setCreated(LocalDateTime.now().toString());
        return wordsRepo.save(wordRecord);
    }

    @PostMapping(path = "create_user")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        user.setCreated(LocalDateTime.now().toString());
        try {
            return usersRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            //TODO notify the user that the username is not unique
            return null;
        }
    }

    @GetMapping(path = "all_words")
    @ResponseBody
    public List<WordRecord> getAllWords() {
        return new ArrayList<>(wordsRepo.findAll());
    }

    @GetMapping(path = "all_users")
    @ResponseBody
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepo.findAll());
    }

    @GetMapping(path = "all_words_where_user_id/{user_id}")
    @ResponseBody
    public List<WordRecord> getAllWordsByUserId(@PathVariable String user_id) {
        return wordsRepo.findAllByUserId(Integer.parseInt(user_id));
    }

    @PutMapping(path = "update_word/{word_id}")
    @ResponseBody
    public WordRecord updateWord(@PathVariable Integer word_id, @RequestBody WordRecord wordRecord) {
        wordRecord.setId(word_id);
        return wordsRepo.save(wordRecord);
    }

    @DeleteMapping(path = "delete_word/{word_id}")
    @ResponseBody
    public void deleteWord(@PathVariable Integer word_id) {
        wordsRepo.deleteById(word_id);
    }
}