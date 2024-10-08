import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgFor, NgIf } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { RouterLink } from '@angular/router';
import { Course } from '../models/course.model';
import { ReactiveFormsModule, Validators, FormGroup, FormControl } from '@angular/forms';
import { Author } from '../models/author.model';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink, ReactiveFormsModule],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.css'
})
export class CoursesComponent implements OnInit {
  courses: Course[] = [];
  error: string | null = null;
  authorIds: number[] = [];
  showForm: boolean = false;
  isEdit: boolean = false;
  currentCourseId: number | null = null;
  courseForm = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(3)]),
    authorId: new FormControl(1, Validators.required),
    cost: new FormControl(0, [Validators.required, Validators.min(0)]),
  });

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchCourses();
    this.fetchAuthors();
  }

  fetchCourses(){
    this.http.get<Course[]>('http://localhost:8080/courses')
      .pipe(
        catchError(error => {
          this.error = 'Failed to get courses!';
          return of([]);
        })
      )
      .subscribe(data => {
        if (data) {
          this.courses = data;
          this.error = null;
        } else {
          this.error = 'Failed to get courses!';
        }
      });
  }

  fetchAuthors(){
    this.http.get<Author[]>('http://localhost:8080/authors')
      .pipe(
        catchError(error => {
          this.error = 'Failed to get authors!';
          return of([]);
        })
      )
      .subscribe(data => {
        if (data) {
          for (const author of data) {
            if (author && author.id !== undefined) {
              this.authorIds.push(author.id);
            }
          }
          this.error = null;
        } else {
          this.error = 'Failed to get authors!';
        }
      });
  }

  deleteCourse(id: number){
    this.http.delete(`http://localhost:8080/courses/${id}`)
      .pipe(
        catchError(error => {
          this.error = 'Failed to delete course!';
          return of(null);
        })
      )
      .subscribe(response => {
        this.courses = this.courses.filter(course => course.id !== id);
        this.error = null;
      });
  }

  toggleCreateForm() {
    this.showForm = !this.showForm;
    this.isEdit = false;
    this.courseForm.reset({ title: '', authorId: 1, cost: 0 });
    this.error = null;
    this.currentCourseId = null;
  }

  uploadCourse() {
    if (this.courseForm.valid) {
      if(this.isEdit){
        this.editCourse();
      } else {
        this.createCourse();
      }
    }
  }

  editCourse(){
    this.http.put<Course>(`http://localhost:8080/courses/${this.currentCourseId}`, this.courseForm.value)
      .pipe(
        catchError(error => {
          this.error = 'Failed to create course!';
          console.log(error);
          return of(null);
        })
      )
      .subscribe(course => {
        if (course) {
          const index = this.courses.findIndex(c => c.id === this.currentCourseId);

          if (index !== -1) {
            this.courses[index] = course;

          this.courseForm.reset({ title: '', authorId: 1, cost: 0 });
          this.showForm = false;
          this.isEdit = false;
          this.currentCourseId = null;
          this.error = null;
          }
        }
      });
  }

  createCourse(){    
    this.http.post<Course>('http://localhost:8080/courses', this.courseForm.value)
      .pipe(
        catchError(error => {
          this.error = 'Failed to create course!';
          console.log(error);
          return of(null);
        })
      )
      .subscribe(course => {
        if (course) {
          this.courses.push(course);
          this.courseForm.reset({ title: '', authorId: 1, cost: 0 });
          this.showForm = false;
          this.currentCourseId = null;
          this.error = null;
        }
      });
  }

  openEditForm(id: number){
    const course = this.courses.find(c => c.id === id);
    this.currentCourseId = id;

    if (course) {
      this.courseForm.patchValue({
        title: course.title,
        authorId: course.authorId,
        cost: course.cost
      });
    }

    this.showForm = true;
    this.isEdit = true;
    this.error = null;
  }
}
