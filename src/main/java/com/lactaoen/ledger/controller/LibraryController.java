package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.LibraryMapper;
import com.lactaoen.ledger.model.form.BookForm;
import com.lactaoen.ledger.model.form.ChapterUpdateForm;
import com.lactaoen.ledger.model.form.WordForm;
import com.lactaoen.ledger.model.library.Book;
import com.lactaoen.ledger.model.library.Chapter;
import com.lactaoen.ledger.model.library.Word;
import com.mysql.cj.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
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
        List<Book> books = libraryMapper.getAllBooks();

        Map<String, List<Book>> bookMap = books.stream().collect(groupingBy(Book::getStatus));

        for (Map.Entry<String, List<Book>> entry : bookMap.entrySet()) {
            List<Book> sortedBooks = entry.getValue().stream().sorted(comparing(Book::getName)).collect(toList());
            entry.setValue(sortedBooks);
        }

        model.addAttribute("books", bookMap);
        return "library";
    }

    @GetMapping("hada")
    public String getTodoDashboard(Model model) {
        List<Book> books = libraryMapper.getAllBooks();
        for (Book book : books) {
            List<Chapter> todoChapters = book.getChapters().stream().filter(Chapter::isTodo).collect(toList());
            book.setChapters(todoChapters);
        }


        model.addAttribute("books", books.stream().filter(book -> book.getChapters().size() > 0).sorted(comparing(Book::getName)).collect(toList()));
        return "hada";
    }

    @GetMapping("/unfinished")
    public String getUnfinishedWordsDashboard(Model model) {
        model.addAttribute("words", libraryMapper.getAllUnfinishedWords());
        return "unfinished";
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
        model.addAttribute("chapterName", libraryMapper.getChapterNameById(chapterId));
        return "chapter";
    }

    @PostMapping("/book")
    public RedirectView createBook(@ModelAttribute("book") BookForm bookForm, RedirectAttributes model) {
        List<Chapter> chapters = Arrays.stream(bookForm.getChapters().split("\\n")).map(String::trim).filter(word -> !StringUtils.isNullOrEmpty(word)).map(Chapter::new).collect(toList());
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setChapters(chapters);

        libraryMapper.createBook(book);

        int bookId = book.getBookId();
        book.getChapters().forEach(chapter -> {
            chapter.setBookId(bookId);
            libraryMapper.createChapter(chapter);
        });

        return new RedirectView("/library");
    }

    @ResponseBody
    @PostMapping("/chapter")
    public int updateChapter(@RequestBody ChapterUpdateForm chapterUpdateForm) {
         return libraryMapper.updateChapter(chapterUpdateForm);
    }

    @ResponseBody
    @PutMapping("/word/batch")
    public int markWordsAsDone(@RequestBody List<Integer> wordIds, RedirectAttributes model) {
         wordIds.forEach(libraryMapper::updateWord);
        return 1;
    }

    @PostMapping("/word")
    public RedirectView createWords(@ModelAttribute("word") WordForm wordForm, RedirectAttributes model) {
        int chapterId = wordForm.getChapterId();

        Arrays.stream(wordForm.getWords().split("\\n")).map(String::trim).filter(word -> !StringUtils.isNullOrEmpty(word)).map(val -> {
            Word word = new Word();
            word.setChapterId(chapterId);
            word.setValue(val);
            return word;
        }).forEach(libraryMapper::createWord);

        return new RedirectView("/library");
    }
}
