package com.xzp.smartcampus.common.utils;

import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.vo.TreeVo;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.vo.MenuTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author SGS
 */
@Slf4j
public class TreeUtil {

    /**
     * 将model转换为vo对象
     *
     * @param treeModels treeModels
     * @param <T>        TreeVo
     * @return List<T>
     */
    public static <T extends TreeVo> List<T> modelToTreeVo(List<? extends BaseModel> treeModels, T t) {
        if (CollectionUtils.isEmpty(treeModels)) {
            return Collections.emptyList();
        }
        // 转为vo
        List<TreeVo> vos = treeModels.stream().map(t::modelToVo).collect(Collectors.toList());
        Map<String, List<TreeVo>> pidToVoListMap = new LinkedHashMap<>(16);
        vos.forEach(item -> {
            List<TreeVo> treeVos = pidToVoListMap.computeIfAbsent(item.getPid(), k -> new ArrayList<>());
            treeVos.add(item);
        });

        // 父节点不在映射中视为根节点
        List<TreeVo> rootVos = vos.stream().filter(item -> !pidToVoListMap.containsKey(item.getId())).collect(Collectors.toList());
        // 组装成树
        assembleTree(rootVos, pidToVoListMap);
        return (List<T>) rootVos;
    }

    /**
     * 组装成树
     *
     * @param rootVos        rootVos
     * @param pidToVoListMap pidToVoListMap
     */
    private static void assembleTree(List<TreeVo> rootVos, Map<String, List<TreeVo>> pidToVoListMap) {
        if (CollectionUtils.isEmpty(rootVos)) {
            return;
        }
        rootVos.forEach(item -> {
            List<TreeVo> child = pidToVoListMap.get(item.getId());
            if (CollectionUtils.isEmpty(child)) {
                return;
            }
            item.setChild(child);
            assembleTree(child, pidToVoListMap);
        });
    }

    /**
     * path转换为 ids
     *
     * @param treePaths treePaths
     * @return List<String>
     */
    public static List<String> treePathToIds(List<String> treePaths) {
        if (CollectionUtils.isEmpty(treePaths)) {
            return Collections.emptyList();
        }
        Set<String> ids = new LinkedHashSet<>(treePaths.size());
        treePaths.forEach(treePath -> {
            if (StringUtils.isNotBlank(treePath)) {
                ids.addAll(Arrays.asList(treePath.split("##")));
            }
        });
        return new ArrayList<>(ids);
    }
}
