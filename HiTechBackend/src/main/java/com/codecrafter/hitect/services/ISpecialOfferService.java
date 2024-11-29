package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.SpecialOffer;

import java.util.List;

public interface ISpecialOfferService {

    SpecialOffer addSpecialOffer(SpecialOffer specialOffer);

    List<SpecialOffer> getAllSpecialOffers();

    Boolean updateSpecialOffer(SpecialOffer specialOffer);

    Boolean deleteSpecialOffer(Long specialOfferId);
}
