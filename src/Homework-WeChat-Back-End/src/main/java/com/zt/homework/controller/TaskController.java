package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.*;
import com.zt.homework.entity.Task;
import com.zt.homework.service.TaskService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping(value = "/task")
    public ResponseEntity<Result> createTask(@RequestBody Task task) {
        Integer taskId = taskService.createTask(task);
        JSONObject data = new JSONObject();
        data.put("taskId", taskId);
        return ResponseEntity.ok(ResultUtil.success(data));
    }

    /**
     * 获取用户的任务列表
     * @return
     */
    @GetMapping(value = "/task")
    public ResponseEntity<Result> getAllTask() {
        Integer userId = AppContext.getCurrentUserId();
        List<TaskListItem> taskList = taskService.getTaskByUserId(userId);
        return ResponseEntity.ok(ResultUtil.success(taskList));
    }

    @GetMapping(value = "/task/{courseId}")
    public ResponseEntity<Result> getTotalTaskById(@PathVariable Integer courseId) {
        TotalTaskDto totalTask=taskService.getTotalTaskDtoByCourseId(courseId);
        return ResponseEntity.ok(ResultUtil.success(totalTask));
    }

    @GetMapping(value = "/task/{courseId}/{taskId}")
    public ResponseEntity<Result> getTaskById(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        TaskDto taskDto = taskService.getTaskDtoByTaskIdCourseId(taskId, courseId);
        return ResponseEntity.ok(ResultUtil.success(taskDto));
    }

    @PutMapping(value = "/task/{courseId}/{taskId}")
    public ResponseEntity<Result> endTask(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        taskService.endTask(courseId, taskId);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @GetMapping(value = "/task/{courseId}/{taskId}/end")
    public ResponseEntity<Result> getEndTask(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        EndTaskDto endTaskDto = taskService.getEndTaskDto(courseId, taskId);
        return ResponseEntity.ok(ResultUtil.success(endTaskDto));
    }
}
