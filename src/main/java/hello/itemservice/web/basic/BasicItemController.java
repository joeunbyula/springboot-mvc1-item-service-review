package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 20000, 10));
        itemRepository.save(new Item("itemB", 10000, 20));
        itemRepository.save(new Item("itemC", 15000, 5));
    }

    @GetMapping("/{itemId}")
    public String findById(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String save(Item item, Model model) {
//        Item saveItem = itemRepository.save(item);
//        model.addAttribute("item", saveItem);
//        return "basic/item";
//    }

    /**
     * @RequestParam 활용
     *
     * @param itemName
     * @param price
     * @param quantity
     * @param model
     * @return
     */
    //@PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName
            , @RequestParam Integer price
            , @RequestParam Integer quantity
            , Model model) {

        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttrbute 이름설정 활용
     * @param item
     * @return
     */
    //@PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item) {

       itemRepository.save(item);

        //@ModelAttribute("item") 설정함으로써 자동 추가, 생략가능
       // model.addAttribute("item", saveItem);

        return "basic/item";
    }

    /**
     * @ModelAttrbute 활용
     * @param item
     * @return
     */
    //@PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item) {

        itemRepository.save(item);

        //@ModelAttribute("item") 설정함으로써 자동 추가, 생략가능
        // model.addAttribute("item", saveItem);

        return "basic/item";
    }

    /**
     * @ModelAttrbute 생략
     * @param item
     * @return
     */
   // @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

    /**
     * PRG 적용
     * @param item
     * @return
     */
   // @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);

        return "redirect:/basic/items/"+item.getId();
    }

    /**
     * PRG 적용
     * @param item
     * @return
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item saveItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/item/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, Item updateParam) {
        itemRepository.update(itemId, updateParam);

        return "redirect:/basic/items/{itemId}";
    }
}
