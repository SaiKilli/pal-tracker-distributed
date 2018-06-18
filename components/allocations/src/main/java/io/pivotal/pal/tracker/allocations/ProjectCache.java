package io.pivotal.pal.tracker.allocations;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProjectCache {

    private Map<Long, ProjectInfo> projectCache;

    public ProjectCache() {
        this.projectCache = new ConcurrentHashMap<>();
    }

    public void cacheProjectInfo(Long projectId, ProjectInfo projectInfo){
        this.projectCache.put(projectId, projectInfo);
    }

    public ProjectInfo getFromCache(Long projectId){
        return this.projectCache.get(projectId);
    }
}
