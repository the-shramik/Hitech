package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.SpecialOffer;
import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.ISpecialRepository;
import com.codecrafter.hitect.services.ISpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements ISpecialOfferService {

    private final ISpecialRepository specialRepository;

    @CacheEvict(value = "specialoffers", allEntries = true)
    @Override
    public SpecialOffer addSpecialOffer(SpecialOffer specialOffer) {
        return specialRepository.save(specialOffer);
    }


    @Cacheable(value = "specialoffers")
    public List<SpecialOffer> getAllSpecialOffers() {
        return specialRepository.findAll();
    }

    @CacheEvict(value = "specialoffers", allEntries = true)
    @Override
    public Boolean updateSpecialOffer(SpecialOffer specialOffer) {
        SpecialOffer existedSpecialOffer=specialRepository.findById(specialOffer.getSpecialOfferId()).orElseThrow(
                ()->new ResourceNotFoundException("SpecialOffer is not present with this details!"));

        existedSpecialOffer.setSpecialOffer(specialOffer.getSpecialOffer());
        specialRepository.save(existedSpecialOffer);
        return true;
    }

    @CacheEvict(value = "specialoffers", allEntries = true)
    @Override
    public Boolean deleteSpecialOffer(Long specialOfferId) {

        specialRepository.deleteById(specialOfferId);
        return true;
    }
}
