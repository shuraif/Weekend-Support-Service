package com.ms.ws.service;

import java.util.List;

public interface HistoryService {

  boolean saveHistory(String userId, String action, String details);

  List<String> getHistoryByUserId(String userId);

  List<String> getAllHistory();

  List<String> getHistoryByAction(String action);

  List<String> getHistoryByDetails(String details);

}
