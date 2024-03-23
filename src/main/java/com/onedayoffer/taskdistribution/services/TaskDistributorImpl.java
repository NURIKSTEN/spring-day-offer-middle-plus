package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskDistributorImpl implements TaskDistributor {
    private static final int MIN_WORKLOAD = 360; // Минимум 6 часов работы в минутах
    private static final int MAX_WORKLOAD = 420; // Максимум 7 часов работы в минутах

    public void distribute(List<EmployeeDTO> employees, List<TaskDTO> tasks) {

        tasks.sort(Comparator.comparing(TaskDTO::getPriority)
                .thenComparing(Comparator.comparing(TaskDTO::getLeadTime).reversed()));

        int taskNumber = 0;
        while (taskNumber < tasks.size()) {
            for (EmployeeDTO employee : employees) {
                if (taskNumber >= tasks.size()) {
                    return;
                }
                TaskDTO task = tasks.get(taskNumber);
                if (employee.getTotalLeadTime() + task.getLeadTime() < MAX_WORKLOAD) {
                    employee.getTasks().add(task);
                    taskNumber++;
                } else if (employees.indexOf(employee) == employees.size() - 1) {
                    taskNumber++;
                }
            }
        }
    }
}
