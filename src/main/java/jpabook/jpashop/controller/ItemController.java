package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * 상품 등록 조회
     */
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book(); // 여기에서 set을 해주는 것 보다는 createBook으로 해서 넘기는 코드가 유지보수상 더 좋음 (실무에서는 이렇게 안씀)
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";

    }

    @PostMapping("items/{itemId}/edit")  // 준영속 엔터티를 수정하는 방법
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        //Book book = new Book();
        //book.setId(form.getId());  // id가 넘어오는건 보안에 위험함, user가 id에 접근할 수 있는지 체크하는 비즈니스로직이 있어야함
        //book.setIsbn(form.getIsbn());
        //book.setName(form.getName());
        //book.setPrice(form.getPrice());
        //book.setStockQuantity(form.getStockQuantity());
        //book.setAuthor(form.getAuthor());
        itemService.updateItem(itemId, form.getPrice(), form.getName());

        //itemService.saveItem(book);
        return "redirect:/items";
    }
}
