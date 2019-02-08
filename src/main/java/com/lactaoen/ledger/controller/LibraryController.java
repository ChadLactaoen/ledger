package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.LibraryMapper;
import com.lactaoen.ledger.model.form.BookForm;
import com.lactaoen.ledger.model.form.WordForm;
import com.lactaoen.ledger.model.library.Book;
import com.lactaoen.ledger.model.library.Chapter;
import com.mysql.cj.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final LibraryMapper libraryMapper;

    public LibraryController(LibraryMapper libraryMapper) {
        this.libraryMapper = libraryMapper;
    }

    @GetMapping
    public String getLibraryDashboard(Model model) {
        model.addAttribute("books", libraryMapper.getAllBooks());
        return "library";
    }

    @GetMapping("/unfinished")
    public String getUnfinishedWordsDashboard(Model model) {
        model.addAttribute("words", libraryMapper.getAllUnfinishedWords());
        return "unfinished";
    }

    @GetMapping("/todochapter")
    public String getTodoChapters(Model model) {
        model.addAttribute("chapters", libraryMapper.getTodoChapters());
        return "todochapter";
    }

    @GetMapping("/book")
    public String getBookForm(Model model) {
        model.addAttribute("book", new BookForm());
        return "book";
    }

    @GetMapping("/chapter/{chapterId}")
    public String getWordForm(@PathVariable("chapterId") int chapterId, Model model) {
        WordForm wordForm = new WordForm();
        wordForm.setChapterId(chapterId);
        model.addAttribute("word", wordForm);
        return "chapter";
    }

    @PostMapping("/book")
    public RedirectView createBook(@ModelAttribute("book") BookForm bookForm, RedirectAttributes model) {
        List<Chapter> chapters = Arrays.stream(bookForm.getChapters().split("\\n")).map(String::trim).filter(word -> !StringUtils.isNullOrEmpty(word)).map(Chapter::new).collect(toList());
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setChapters(chapters);

//        chapters.stream().forEach(chapter -> System.out.println(chapter.getName()));

        libraryMapper.createBook(book);

        int bookId = book.getBookId();
        book.getChapters().forEach(chapter -> {
            chapter.setBookId(bookId);
            libraryMapper.createChapter(chapter);
        });

        return new RedirectView("/library");
    }

    @PostMapping("/word")
    public int createWords(@RequestBody Chapter chapter) {
        int chapterId = chapter.getChapterId();
        chapter.getWords().forEach(word -> {
            word.setChapterId(chapterId);
            libraryMapper.createWord(word);
        });

        return 1;
    }
}
