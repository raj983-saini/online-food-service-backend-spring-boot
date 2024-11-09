package com.example.roleBased.Services;

import com.example.roleBased.Dto.RestaurantDto;
import com.example.roleBased.Dto.ResturantDetailDto;
import com.example.roleBased.Entity.Adressing;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.AdressRepository;
import com.example.roleBased.Repository.ResturantRepository;
import com.example.roleBased.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    public ResturantRepository resturantRepository;
    @Autowired
    public AdressRepository adressRepository;

    @Autowired
    public UserRepository userRepository;
    @Transactional
    public Restaurant createRestaurant(ResturantDetailDto detailDto, User user) {
        // Check if a restaurant with the same name or address already exists
        Optional<Restaurant> existingRestaurant = resturantRepository.findByName(detailDto.getName());

        if (existingRestaurant.isPresent()) {
            throw new IllegalStateException("A restaurant with this name already exists.");
        }

        Adressing adressing = adressRepository.save(detailDto.getAdressing());

        Restaurant restaurant = new Restaurant();
        restaurant.setAdressing(adressing);
        restaurant.setName(detailDto.getName());
        restaurant.setContactInfo(detailDto.getContactInfo());
        restaurant.setCuisineType(detailDto.getCusiontype());
        restaurant.setDescription(detailDto.getDescription());
        restaurant.setOpeningHour(detailDto.getOpeninghour());
        restaurant.setImage(detailDto.getImage());
        restaurant.setProfilePicture(detailDto.getProfilepitcher());
        restaurant.setRegisterdate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setOpen(true);  // Set default state for opening

        return resturantRepository.save(restaurant);
    }
@Transactional
    public Restaurant updateRestaurant(Long restaurantId, ResturantDetailDto detailDto) {
        Restaurant restaurant = resturantRepository.findResturantById(restaurantId);

        if (restaurant != null) {
            // Update fields
            restaurant.setName(detailDto.getName());
            restaurant.setCuisineType(detailDto.getCusiontype());
            restaurant.setDescription(detailDto.getDescription());
            restaurant.setOpeningHour(detailDto.getOpeninghour());

            // Update the images
            if (detailDto.getImage() != null) {
                restaurant.setImage(detailDto.getImage());
            }
            return resturantRepository.save(restaurant);
        } else {
            throw new EntityNotFoundException("Restaurant not found with id: " + restaurantId);
        }
    }

    @Transactional
    public void deleteResturant(Long resturantId ){
        Restaurant restaurant = resturantRepository.findResturantById(resturantId);

        resturantRepository.delete(restaurant);
    }
    @Transactional
    public List<Restaurant> getAllResturant(){
        List<Restaurant> restaurants = resturantRepository.findAll();

        // Initialize lazy-loaded fields
        restaurants.forEach(restaurant -> {
        });
        return  restaurants;
    }

    @Transactional
    public List<Restaurant> searchByKeyword(String keyword) {
        // If the keyword is null or empty, return an empty list or handle it accordingly
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword must not be empty");
        }

        // Find restaurants by keyword using the repository's search query
        List<Restaurant> restaurants = resturantRepository.findBySerachQuery(keyword.trim());

        // Initialize lazy-loaded fields if they will be accessed
        restaurants.forEach(restaurant -> {
        });

        return restaurants;
    }

    @Transactional
    public Restaurant findResturantById(Long id) throws Exception{
        Optional <Restaurant> opt = resturantRepository.findById(id);


        if (!opt.isPresent()){
            throw  new Exception("Resturant not found");
        }
        return  opt.get();

    }
    @Transactional
    public  Restaurant getResturantByUserId(Long userId) throws Exception {
        Restaurant restaurant = resturantRepository.findByOwnerId(userId);
        if (restaurant == null){
            throw  new Exception("Resturant not found");
        }

        return  restaurant;
    }
    @Transactional
    public RestaurantDto addFavurate(Long resturantId , User user ){
        Restaurant restaurant = resturantRepository.findResturantById(resturantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setDescription(restaurant.getDescription());
        dto.setPitcher(restaurant.getImage());
        dto.setTittle(restaurant.getName());
        boolean faourate = false;
        List<RestaurantDto> favurate = user.getFavurate();
        for (RestaurantDto fav : favurate){
            if(fav.getId().equals(resturantId)){
                faourate = true;
                break;
            }
        }
        if (faourate){
            favurate.removeIf(favortie -> favortie.getId().equals(resturantId));
        }
        else {
            favurate.add(dto);
        }
  userRepository.save(user);
        return  dto;
    }
    @Transactional
    public  Restaurant updateResturantStatus(Long id,User user){
        Restaurant restaurant = resturantRepository.findResturantById(id);
        return  resturantRepository.save(restaurant);
    }

}
