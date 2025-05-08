package com.utitech.carwash.hardware;

import com.utitech.carwash.model.Tariffs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelayServiceImpl implements RelayService{

    private final RelayContext relayContext;

    @Override
    public void toggleLamp(int relayId) {
        relayContext.toggle(relayId);
    }

    @Override
    public boolean startWashing(int relayId, int price, Tariffs tariff, boolean paymentMethod) {
        return false;
    }

    @Override
    public void adminOverride(int relayId) {

    }

    @Override
    public boolean startWashing(int relayId, int price, Tariffs tariff) {
        return true;
    }
}