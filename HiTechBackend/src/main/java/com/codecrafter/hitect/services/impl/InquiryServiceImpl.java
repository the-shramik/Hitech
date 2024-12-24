package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.InquiryForm;
import com.codecrafter.hitect.repository.IInquiryRepository;
import com.codecrafter.hitect.services.IInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements IInquiryService {

    private final IInquiryRepository inquiryRepository;

    @Override
    public InquiryForm inquiryForm(InquiryForm inquiryForm) {
        return inquiryRepository.save(inquiryForm);
    }

    @Override
    public List<InquiryForm> getInquiryForms() {
        return inquiryRepository.findAll();
    }

    @Override
    public Boolean deleteInquiry(Long inquiryId) {

        inquiryRepository.deleteById(inquiryId);
        return true;
    }
}
