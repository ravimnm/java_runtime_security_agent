//package org.jrsa.backend.controller;
//
//import java.util.List;
//
//import org.jrsa.backend.entity.Task;
//import org.jrsa.backend.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/restapi/tasks")
//public class TaskController {
//
//@Autowired
//TaskRepository repo;
//
//@PostMapping
//public Task create(@RequestBody Task task){
//
//return repo.save(task);
//
//}
//
//@GetMapping
//public List<Task> getAll(){
//
//return repo.findAll();
//
//}
//
//@DeleteMapping("/{id}")
//public void delete(@PathVariable String id){
//
//repo.deleteById(id);
//
//}
//
//}