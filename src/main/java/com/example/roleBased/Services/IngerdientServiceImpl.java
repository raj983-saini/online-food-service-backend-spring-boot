package com.example.roleBased.Services;

import com.example.roleBased.Dto.IengerdientDto;
import com.example.roleBased.Dto.IngerdientCategoryDto;
import com.example.roleBased.Entity.IngerdientCatagaries;
import com.example.roleBased.Entity.Ingerident;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.IngerdientCategoryrepostory;
import com.example.roleBased.Repository.IngerdientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngerdientServiceImpl implements  IngerdientService{

    @Autowired
    IngerdientCategoryrepostory ingerdientCategoryrepostory;
    @Autowired
    IngerdientRepository ingerdientRepository;
    @Autowired
    RestaurantService restaurantService;
    @Override
    public IngerdientCatagaries createIngerdientCategory(IngerdientCategoryDto dto) throws Exception {

        Restaurant restaurant = restaurantService.findResturantById(dto.getRestaurantId()) ;
        IngerdientCatagaries ingerdientCatagaries = new IngerdientCatagaries();
        ingerdientCatagaries.setName(dto.getName());
        ingerdientCatagaries.setRestaurant(restaurant);
//        ingerdientCatagaries.set
        return ingerdientCategoryrepostory.save(ingerdientCatagaries);

    }

    @Override
    public IngerdientCatagaries findIngerdientCatagariesById(Long id) throws Exception {
       Optional <IngerdientCatagaries> opt =  ingerdientCategoryrepostory.findById(id);
        if (opt.isEmpty()) {
            throw  new Exception("Ingerdient Not Found");
        }
        return opt.get();
    }

    @Override
    public List<IngerdientCatagaries> findIngerdientCatagariesByRestaurantId(Long restaurantId ) throws Exception {
        Restaurant restaurant = restaurantService.findResturantById(restaurantId);
        List <IngerdientCatagaries> ingerdientCatagaries = ingerdientCategoryrepostory.findByRestaurantId(restaurant.getId());


        return ingerdientCatagaries;
    }

    @Override
    public Ingerident createIngerdientItem(IengerdientDto dto) throws Exception {
        Restaurant restaurant = restaurantService.findResturantById(dto.getRestaurantId()) ;
        IngerdientCatagaries ingerdientCatagaries = findIngerdientCatagariesById(dto.getIngerdientCatagaries());
Ingerident ingerident = new Ingerident();
ingerident.setName(dto.getName());
ingerident.setRestaurant(restaurant);
ingerident.setCatagaries(ingerdientCatagaries  );

Ingerident ingerident1 = ingerdientRepository.save(ingerident);
ingerdientCatagaries.getIngeridents().add(ingerident1);
        return ingerident1;
    }

    @Override
    public List<Ingerident> findRestaurantIngerident(Long restaurantId) throws Exception {
        return ingerdientRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Ingerident updateStock(Long id) throws Exception {
        Optional<Ingerident> ingerident = ingerdientRepository.findById(id);
        if (ingerident.isEmpty()){
            throw  new Exception( " Ingerdient not found");
        }
        Ingerident ingerident1 = ingerident.get();
        ingerident1.setInStoke(!ingerident1.isInStoke());

        return ingerdientRepository.save(ingerident1);
    }
}
