package de.telran.pizza.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithUserDetails("manager")
public class DishControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DishRepository dishRepository;

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    public void testGetAllDishes() throws Exception {
        this.mockMvc.perform(get("/api/manager/dishes/get_all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pepperoni")));
    }

    private String jsonMapper(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    @Test
    public void testCreate() throws Exception {
        Dish dish = Dish.builder()
                .nameEn("test")
                .nameRu("тест")
                .price(new BigDecimal(10))
                .category(Category.builder().id(1).build())
                .build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(post("/api/manager/dishes/create")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }
    @Test
    public void testCreateInvalidInputNameEn() throws Exception {
        Dish dish = Dish.builder()
                .nameEn("тест")
                .nameRu("тест")
                .price(new BigDecimal(10))
                .category(Category.builder().id(1).build())
                .build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(post("/api/manager/dishes/create")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testCreateInvalidInputPrice() throws Exception {
        Dish dish = Dish.builder()
                .nameEn("test")
                .nameRu("тест")
                .price(new BigDecimal(-10))
                .category(Category.builder().id(1).build())
                .build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(post("/api/manager/dishes/create")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testCreateInvalidInputCategories() throws Exception {
        Dish dish = Dish.builder()
                .nameEn("test")
                .nameRu("тест")
                .price(new BigDecimal(10))
                .category(Category.builder().build())
                .build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(post("/api/manager/dishes/create")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetDish() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("nameEn")));
    }
    @Test
    public void testGetDishInvalidInput1() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update?id=fhdfh"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testGetDishInvalidInput2() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update?id="))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testGetDishInvalidInput3() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testGetDishInvalidInput4() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update?id=-123"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void testGetDishInvalidInput5() throws Exception {
        mockMvc.perform(get("/api/manager/dishes/update?id=9999999"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdate() throws Exception {
        Dish dishId = dishRepository.findByNameEn("test").orElse(null);
        if (dishId == null) return;

        Dish dish = Dish.builder()
                .id(dishId.getId())
                .nameEn("testNew")
                .nameRu("тест123")
                .price(new BigDecimal(20))
                .category(Category.builder().id(1).build())
                .build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(put("/api/manager/dishes/update")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateInvalidInput1() throws Exception {
        ItemDTO dish = ItemDTO.builder().build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(put("/api/manager/dishes/update")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
//    @Transactional
    public void testDelete() throws Exception {
        Dish dishId = dishRepository.findByNameEn("test").orElse(null);
        if (dishId == null) return;

        ItemDTO dish = ItemDTO.builder().itemId(dishId.getId()).build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(delete("/api/manager/dishes/delete")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteInvalidInput1() throws Exception {
        ItemDTO dish = ItemDTO.builder().build();
        String requestJson = jsonMapper(dish);

        mockMvc.perform(delete("/api/manager/dishes/delete")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}