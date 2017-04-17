package com.xix.cleanMvpArchitecture.data.cache;

import android.content.Context;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.xix.cleanMvpArchitecture.data.cache.fileManager.FileManager;
import com.xix.cleanMvpArchitecture.data.cache.fileManager.Serializer;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.di.ApplicationContext;
import com.xix.cleanMvpArchitecture.domain.ThreadExecutor;
import io.reactivex.Observable;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.xix.cleanMvpArchitecture.utils.CommonUtils.chechNotNull;
/**
 * {@link DataCache} implementation.
 */
@Singleton
public class DataCacheImpl implements DataCache {
    private static final String LOG_TAG = DataCacheImpl.class.getSimpleName();
    private static final String SETTINGS_FILE_NAME = "com.xix.mvp.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "item_list";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000; //10 min
    private static final int ITEM_LIST_ID = 151611;

    private final Context context;
    private final File cacheDir;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link DataCacheImpl}.
     *
     * @param context {@link ApplicationContext}
     * @param serializer {@link Serializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject DataCacheImpl(@ApplicationContext Context context, Serializer serializer, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || serializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir(); // directory for caching
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override public Observable<List<Item>> getItemList() {
        return Observable.create(emitter -> {
            final File userEntityFile = buildFile(ITEM_LIST_ID);
            final String fileContent = fileManager.readFileContent(userEntityFile);

            Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
            final List<Item> itemList = serializer.deserialize(fileContent, listType);

            if (itemList != null) {
                emitter.onNext(itemList);
                emitter.onComplete();
            } else {
                emitter.onError(new Exception("Not Found Exception"));
            }
        });
    }

    @Override public void putItems(List<Item> itemList) {

        chechNotNull(itemList);
        final File userEntityFile = this.buildFile(ITEM_LIST_ID);
        if (!isCached(ITEM_LIST_ID)) {
            final String jsonString = serializer.serialize(itemList);
            this.executeAsynchronously(new CacheWriter(this.fileManager, userEntityFile, jsonString));
            this.setLastCacheUpdateTimeMillis();
        }
    }

    @Override public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        Log.d(LOG_TAG, "isExpired: ");

        long time;
        boolean expired = ((time = currentTime - lastUpdateTime) > EXPIRATION_TIME);

        Log.d(LOG_TAG, "isExpired: "+time);
        if (expired) {
            this.clearAll();
        }

        return expired;
    }

    @Override public boolean isCached(int id) {
        final File userEntityFile = this.buildFile(id);
        return fileManager.exists(userEntityFile);
    }

    @Override public boolean isItemListCached() {
        return isCached(ITEM_LIST_ID);
    }

    @Override public void clearAll() {
        this.executeAsynchronously(new CacheCleaner(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param fileId The id to build the file.
     * @return A valid file.
     */
    private File buildFile(int fileId) {
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(fileId);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        final long currentMillis = System.currentTimeMillis();
        fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for removing all the cached files
     */
    private static class CacheCleaner implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheCleaner(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
} 
