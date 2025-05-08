package com.utitech.carwash.hardware;

import com.utitech.carwash.model.Tariffs;
import org.springframework.stereotype.Service;

@Service
public interface RelayService {

    void toggleLamp(int relayId);

    boolean startWashing(int relayId, int price, Tariffs tariff, boolean paymentMethod);

    void adminOverride(int relayId);

    boolean startWashing(int relayId, int price, Tariffs tariff);
}