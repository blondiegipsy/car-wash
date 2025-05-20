package com.utitech.carwash.hardware;

import com.utitech.carwash.controller.request.WasherData;
import com.utitech.carwash.model.Tariffs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelayServiceImpl implements RelayService{

    private final RelayContext relayContext;

    @Override
    public boolean startWashing(int relayId, int price, Tariffs tariff, boolean paymentMethod) {
        return false;
    }

    @Override
    public void adminOverride(int relayId) {
        relayContext.toggle(relayId);
    }

    @Override
    public void toggleLamp(int relayId) {
        relayContext.toggle(relayId);
    }

    @Override
    public Map<String, Boolean> getRelayStatus() {
        return relayContext.getStates();
    }

    @Override
    public WasherData getWasherStates() {
        return null;
    }
}