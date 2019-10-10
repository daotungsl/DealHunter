package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.City;
import com.focusteam.dealhunter.entity.TypeStore;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CityRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.iml.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Service("cityService")
public class DefaultCityService implements CityService {
    private HashMap<String, String> hashMap = new HashMap<>();
    @Autowired
    CityRepository cityRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> create(@Valid CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));

        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){

                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);

            }else {

                if (bindingResult.hasErrors()){
                    hashMap.clear();
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError f: fieldErrors
                    ) {
                        hashMap.put(f.getField(), f.getDefaultMessage());
                    }
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Type store data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<City> cityOptional = cityRepository.findByName(cityCreateDto.getName());
                    if (!cityOptional.isPresent()){
                        //System.out.println("Step5");
                        City city = new City(cityCreateDto);
                        cityRepository.save(city);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.CREATED.value())
                                .setData(new CityDto(city))
                                .setMessage("Created city success !").build(), HttpStatus.CREATED);
                    }else {
                        hashMap.clear();
                        hashMap.put("Name", "This city name has exist !");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("City data has errors !").build(), HttpStatus.FORBIDDEN);
                    }
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> getOne(long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()){
            City city = cityOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new CityDto(city))
                    .setMessage("City with id = " + id + " !").build(), HttpStatus.OK);
        }else {
            hashMap.clear();
            hashMap.put("ID", "No city found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<City> cities = cityRepository.findAll();
        if (!cities.isEmpty()){
            List<CityDto> cityDtos = new ArrayList<>();
            CityDto cityDto = null;
            for (City city: cities
            ) {
                cityDto = new CityDto(city);
                cityDtos.add(cityDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(cityDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
        hashMap.clear();
        hashMap.put("City", "No city found !");
        return new ResponseEntity<>(new RESTResponse.Error()
                .addErrors(hashMap)
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData("")
                .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> update(long id, @Valid CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account1 = accountOptional.get();
            if (account1.getTypeAccount() == 1 || account1.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                if (bindingResult.hasErrors()){
                    hashMap.clear();
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError f: fieldErrors
                    ) {
                        hashMap.put(f.getField(), f.getDefaultMessage());
                    }
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Type store data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<City> cityOptional = cityRepository.findById(id);
                    if (cityOptional.isPresent()){
                        City city = cityOptional.get();
                        city.setName(cityUpdate.getName());
                        city.setDescription(cityUpdate.getDescription());
                        city.setUpdated(Calendar.getInstance().getTimeInMillis());
                        city.setStatus(cityUpdate.getStatus());

                        cityRepository.save(city);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.OK.value())
                                .setData(new CityDto(city))
                                .setMessage("Update city data success !").build(), HttpStatus.OK);
                    }else {
                        hashMap.clear();
                        hashMap.put("ID", "No city found with this id !");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                    }
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                Optional<City> cityOptional = cityRepository.findById(id);
                if (cityOptional.isPresent()){
                    City city = cityOptional.get();
                    cityRepository.delete(city);
                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.OK.value())
                            .setData(new CityDto(city))
                            .setMessage("Delete city success !").build(), HttpStatus.OK);
                }else {
                    hashMap.clear();
                    hashMap.put("ID", "No city found with this id !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
