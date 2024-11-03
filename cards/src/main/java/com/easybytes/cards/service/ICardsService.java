package com.easybytes.cards.service;

import com.easybytes.cards.dto.CardsDto;
import com.easybytes.cards.entity.Cards;

public interface ICardsService {

    /**
     * To create a new card
     * @param mobileNumber
     */
    void createCard(String mobileNumber);

    /**
     * To fetch card details
     * @param mobileNumber
     * @return
     */
    CardsDto fetchCardDetails(String mobileNumber);

    /**
     * To update the card details
     * @param cardsDto
     * @return
     */
    boolean updateCardDetails(CardsDto cardsDto);

    /**
     * To delete the card details
     * @param mobileNumber
     * @return
     */
    boolean deleteCardDetails(String mobileNumber);
}
