package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.InquiryForm;

import java.util.List;

public interface IInquiryService {

    InquiryForm inquiryForm(InquiryForm inquiryForm);

    List<InquiryForm> getInquiryForms();

    Boolean deleteInquiry(Long inquiryId);
}
