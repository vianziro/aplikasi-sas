package com.artivisi.school.administration.service.impl;


import com.artivisi.school.administration.domain.Cas;
import com.artivisi.school.administration.domain.Kelas;
import com.artivisi.school.administration.domain.Lesson;
import com.artivisi.school.administration.domain.Major;
import com.artivisi.school.administration.domain.School;
import com.artivisi.school.administration.domain.Student;
import com.artivisi.school.administration.domain.Teacher;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import com.artivisi.school.administration.dao.ApplicationConfigDao;
import com.artivisi.school.administration.dao.CasDao;
import com.artivisi.school.administration.dao.KelasDao;
import com.artivisi.school.administration.dao.LessonDao;
import com.artivisi.school.administration.dao.MajorDao;
import com.artivisi.school.administration.dao.MenuDao;
import com.artivisi.school.administration.dao.PermissionDao;
import com.artivisi.school.administration.dao.RoleDao;
import com.artivisi.school.administration.dao.SchoolDao;
import com.artivisi.school.administration.dao.StudentDao;
import com.artivisi.school.administration.dao.TeacherDao;
import com.artivisi.school.administration.dao.UserDao;
import com.artivisi.school.administration.domain.ApplicationConfig;
import com.artivisi.school.administration.domain.Menu;
import com.artivisi.school.administration.domain.Permission;
import com.artivisi.school.administration.domain.Role;
import com.artivisi.school.administration.domain.User;
import com.artivisi.school.administration.service.BelajarRestfulService;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SuppressWarnings("unchecked")
@Service("belajarRestfulService")

public class BelajarRestfulServiceImpl implements BelajarRestfulService {

    @Autowired
    private ApplicationConfigDao applicationConfigDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private KelasDao kelasDao;  
    @Autowired
    private StudentDao studentDao; 
    @Autowired
    private MajorDao majorDao; 
    @Autowired
    private TeacherDao teacherDao; 
    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private CasDao casDao;
    

    @Override
    public void save(ApplicationConfig ac) {
        applicationConfigDao.save(ac);
    }

    @Override
    public void delete(ApplicationConfig ac) {
        if (ac == null || ac.getId() == null) {
            return;
        }
        applicationConfigDao.delete(ac);
    }

    @Override
    public ApplicationConfig findApplicationConfigById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        return applicationConfigDao.findOne(id);
    }

    @Override
    public Page<ApplicationConfig> findAllApplicationConfigs(Pageable pageable) {
        if(pageable == null){
            pageable = new PageRequest(0, 20);
        }
        return applicationConfigDao.findAll(pageable);
    }

    @Override
    public Long countAllApplicationConfigs() {
        return applicationConfigDao.count();
    }

    @Override
    public Page<ApplicationConfig> findApplicationConfigs(String search, Pageable pageable) {
        if (!StringUtils.hasText(search)) {
            return findAllApplicationConfigs(pageable);
        }
        
        if(pageable == null){
            pageable = new PageRequest(0, 20);
        }

        return applicationConfigDao.search("%" + search + "%", pageable);
    }

    @Override
    public Long countApplicationConfigs(String search) {
        if (!StringUtils.hasText(search)) {
            return countAllApplicationConfigs();
        }
        return applicationConfigDao.count("%" + search + "%");
    }

    @Override
    public void save(Menu m) {
        menuDao.save(m);
    }

    @Override
    public void delete(Menu m) {
        menuDao.delete(m);
    }

    @Override
    public Menu findMenuById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        return menuDao.findOne(id);
    }

    @Override
    public List<Menu> findTopLevelMenu() {
        return menuDao.findTopLevelMenu();
    }
    
    @Override
    public Page<Menu> findAllMenu(Pageable pageable) {
        return menuDao.findAll(pageable);
    }
    
    @Override
    public Long countAllMenu() {
        return menuDao.count();
    }

    @Override
    public List<Menu> findMenuByParent(Menu m) {
        if(m == null || !StringUtils.hasText(m.getId())) {
            return new ArrayList<Menu>();
        }
        return menuDao.findMenuByParent(m.getId());
    }
    
    @Override
    public List<Menu> findMenuNotInRole(Role role){
        if(role == null){
            return new ArrayList<Menu>();
        }
        
        Role r = findRoleById(role.getId());
        if(r == null || r.getMenuSet().isEmpty()){
            return new ArrayList<Menu>();
        }
        
        List<String> ids = new ArrayList<String>();
        for (Menu m : r.getMenuSet()) {
            ids.add(m.getId());
        }
        
        return menuDao.findByIdNotIn(ids);
    }

    @Override
    public void save(Permission m) {
        permissionDao.save(m);
    }

    @Override
    public void delete(Permission m) {
        permissionDao.delete(m);
    }

    @Override
    public Permission findPermissionById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return permissionDao.findOne(id);
    }

    @Override
    public Page<Permission> findAllPermissions(Pageable pageable) {
        if(pageable == null){
            pageable = new PageRequest(0, 20);
        }
        return permissionDao.findAll(pageable);
    }

    @Override
    public Long countAllPermissions() {
        return permissionDao.count();
    }
    
    @Override
    public List<Permission> findPermissionsNotInRole(Role role) {
        if(role == null){
            return new ArrayList<Permission>();
        }
        
        Role r = findRoleById(role.getId());
        if(r == null || r.getPermissionSet().isEmpty()){
            return new ArrayList<Permission>();
        }
        
        List<String> ids = new ArrayList<String>();
        for (Permission p : r.getPermissionSet()) {
            ids.add(p.getId());
        }
        
        return permissionDao.findByIdNotIn(ids);
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public Role findRoleById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        
        Role r = roleDao.findOne(id);
        if(r != null){
            r.getPermissionSet().size();
            r.getMenuSet().size();
        }
        
        return r;
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleDao.findAll(pageable);
    }

    @Override
    public Long countAllRoles() {
        return roleDao.count();
    }

    @Override
    public void save(User m) {
        userDao.save(m);
    }

    @Override
    public void delete(User m) {
        userDao.delete(m);
    }

    @Override
    public User findUserById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return userDao.findOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        if(!StringUtils.hasText(username)){
            return null;
        }
        return userDao.findByUsername(username);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public Long countAllUsers() {
        return userDao.count();
    }

    @Override
    public void save(School m) {
        schoolDao.save(m);
    }

    @Override
    public void delete(School m) {
        schoolDao.delete(m);
    }

    @Override
    public School findSchoolById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return schoolDao.findOne(id);
    }

    @Override
    public Page<School> findAllSchool(Pageable pageable) {
        return schoolDao.findAll(pageable);
    }

    @Override
    public Long countAllSchool() {
        return schoolDao.count();
    }

    @Override
    public void save(Kelas kelas) {
        kelasDao.save(kelas);
    }

    @Override
    public void delete(Kelas kelas) {
        kelasDao.delete(kelas);
    }

    @Override
    public Kelas findKelasById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return kelasDao.findOne(id);
        
    }

    @Override
    public Page<Kelas> findAllKelas(Pageable pageable) {
       return kelasDao.findAll(pageable);
    }

    @Override
    public Long countAllKelas() {
       return kelasDao.count();
    }
    
    @Override
    public void save(Student student) {
        studentDao.save(student);
    }

    @Override
    public void delete(Student student) {
        studentDao.delete(student);
    }

    @Override
    public Student findStudentById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return studentDao.findOne(id);
    }

    @Override
    public Page<Student> findAllStudent(Pageable pageable) {
        return studentDao.findAll(pageable);
    }

    @Override
    public Long countAllStudent() {
        return studentDao.count();
    }
    
    @Override
    public void save(Major mj) {
        majorDao.save(mj);
    }

    @Override
    public void delete(Major mj) {
        majorDao.delete(mj);
    }

    @Override
    public Major findMajorById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return majorDao.findOne(id);
    }

    @Override
    public Page<Major> findAllMajor(Pageable pageable) {
        return majorDao.findAll(pageable);
    }

    @Override
    public Long countAllMajor() {
        return majorDao.count();
    }

    @Override
    public void save(Teacher t) {
        teacherDao.save(t);
    }

    @Override
    public void delete(Teacher t) {
        teacherDao.delete(t);
    }

    @Override
    public Teacher findTeacherById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return teacherDao.findOne(id);
    }

    @Override
    public Page<Teacher> findAllTeacher(Pageable pageable) {
        return teacherDao.findAll(pageable);
    }

    @Override
    public Long countAllTeacher() {
        return teacherDao.count();
    }

    @Override
    public void save(Lesson l) {
        lessonDao.save(l);
    }

    @Override
    public void delete(Lesson l) {
        lessonDao.delete(l);
    }

    @Override
    public Lesson findLessonById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return lessonDao.findOne(id);
    }

    @Override
    public Page<Lesson> findAllLesson(Pageable pageable) {
        return lessonDao.findAll(pageable);
    }

    @Override
    public Long countAllLesson() {
        return lessonDao.count();
    }

    @Override
    public void save(Cas c) {
        casDao.save(c);
    }

    @Override
    public void delete(Cas c) {
        casDao.delete(c);
    }

    @Override
    public Cas findCasById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return casDao.findOne(id);
    }

    @Override
    public Page<Cas> findAllCas(Pageable pageable) {
        return casDao.findAll(pageable);
    }

    @Override
    public Long countAllCas() {
        return casDao.count();
    }

    

}
