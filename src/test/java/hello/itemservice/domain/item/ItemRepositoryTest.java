package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

   ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void remove() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemD", 50000, 30);
        //when
        Item saveItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(saveItem).isEqualTo(findItem);
    }

    @Test
    void findAll() {

        itemRepository.save(new Item("itemA", 20000, 10));
        itemRepository.save(new Item("itemB", 10000, 20));
        itemRepository.save(new Item("itemC", 30000, 5));

        List<Item> items = itemRepository.findAll();
        assertThat(items.size()).isEqualTo(3);
       // assertThat(items).contains()
    }

    @Test
    void update() {
        //given
        Item item = new Item("itemD", 50000, 30);

        //when
        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();
        Item updateParam = new Item("itemA", 20000, 20);
        itemRepository.update(itemId, updateParam);

        //when
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());

    }

}