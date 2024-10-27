package com.bot.storage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Интерфейс хранилища данных пользователей
 */
public abstract class Storage {
    private final ExecutorService threadPool;

    public Storage(){
        this.threadPool = Executors.newFixedThreadPool(1);
    }
    public Storage(int threadCount){
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * @param userId id пользователя
     * @param key название ключа для данных
     * @param value данные
     * @return возвращает передаваемые данные
     */
    public abstract Object setUserData(long userId, String key, Object value) throws Exception;

    /**
     * @param userId id пользователя
     * @param key название ключа для данных
     * @return возвращает данные или null
     */
    public abstract Object getUserData(long userId, String key) throws Exception;

    /**
     * Сбрасывает все данные пользователя
     * @param userId id пользователя
     */
    public abstract void clearUserData(long userId) throws Exception;

    /**
     * запускает setUserData в отдельном потоке
     * @return CompletableFuture
     */
    public CompletableFuture<Object> setUserDataAsync(long userId, String key, Object value){
        CompletableFuture<Object> future = new CompletableFuture<>();
        threadPool.execute(
                ()->{
                    try {
                        future.complete(setUserData(userId, key, value));
                    }
                    catch (Exception ex){
                        future.completeExceptionally(ex);
                    }
                }
        );
        return future;
    }

    /**
     * запускает getUserData в отдельном потоке
     * @return CompletableFuture
     */
    public CompletableFuture<Object> getUserDataAsync(long userId, String key){
        CompletableFuture<Object> future = new CompletableFuture<>();
        threadPool.execute(
                ()->{
                    try {
                        future.complete(getUserData(userId, key));
                    }
                    catch (Exception ex){
                        future.completeExceptionally(ex);
                    }
                }
        );
        return future;
    }

    /**
     * запускает clearUserData в отдельном потоке
     * @return CompletableFuture
     */
    public CompletableFuture<Void> clearUserDataAsync(long userId){
        return CompletableFuture.runAsync(
            ()->{
                try {
                    clearUserData(userId);
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        );
    }
}
