package com.example.roleBased.Services;

import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl  implements  CategoryService{

    @Autowired
    public  RestaurantService restaurantService;

    @Autowired
    public CategoryRepository categoryRepository;

    public Catagaries createCategory(String name, Restaurant restaurant) throws Exception {

        Catagaries catagaries = new Catagaries();
        catagaries.setName(name);
        catagaries.setRestaurant(restaurant);

        return  categoryRepository.save(catagaries);
    }

    @Override
    public List<Catagaries> findCategoryByreturantId(Long id)throws  Exception {
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Catagaries findcategoryById(Long id) throws  Exception {
        Optional <Catagaries> opt = categoryRepository.findById(id);
        if (opt.isEmpty()){
            throw  new Exception("Category Not Found");
        }
        return opt.get();
    }
}
