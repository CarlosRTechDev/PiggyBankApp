package com.example.piggybankapp.controllers;

public interface CallbackService {
    void onFinish(String message);
    void onError(String errorMessage);

}
