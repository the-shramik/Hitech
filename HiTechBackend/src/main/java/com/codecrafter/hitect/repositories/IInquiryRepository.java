package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.InquiryForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInquiryRepository extends JpaRepository<InquiryForm, Long> {
}
