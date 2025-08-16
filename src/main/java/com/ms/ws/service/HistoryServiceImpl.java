package com.ms.ws.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

  @Override
  public boolean saveHistory(String userId, String action, String details) {
    return false;
  }

  @Override
  public List<String> getHistoryByUserId(String userId) {
    return List.of();
  }

  @Override
  public List<String> getAllHistory() {
    return List.of();
  }

  @Override
  public List<String> getHistoryByAction(String action) {
    return List.of();
  }

  @Override
  public List<String> getHistoryByDetails(String details) {
    return List.of();
  }
}
