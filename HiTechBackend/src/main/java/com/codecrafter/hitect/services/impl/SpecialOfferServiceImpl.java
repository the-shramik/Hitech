package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.SpecialOffer;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repository.ISpecialOfferRepository;
import com.codecrafter.hitect.services.ISpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements ISpecialOfferService {

    private final ISpecialOfferRepository specialRepository;

    @Override
    public SpecialOffer addSpecialOffer(SpecialOffer specialOffer) {
        return specialRepository.save(specialOffer);
    }


    public List<SpecialOffer> getAllSpecialOffers() {
        return specialRepository.findAll();
    }

    @Override
    public Boolean updateSpecialOffer(SpecialOffer specialOffer) {
        SpecialOffer existedSpecialOffer=specialRepository.findById(specialOffer.getSpecialOfferId()).orElseThrow(
                ()->new ResourceNotFoundException("SpecialOffer is not present with this details!"));

        existedSpecialOffer.setSpecialOffer(specialOffer.getSpecialOffer());
        specialRepository.save(existedSpecialOffer);
        return true;
    }

    @Override
    public Boolean deleteSpecialOffer(Long specialOfferId) {

        specialRepository.deleteById(specialOfferId);
        return true;
    }
}
