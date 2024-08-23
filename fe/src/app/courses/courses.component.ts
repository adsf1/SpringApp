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
    this.courseForm.reset();
    this.error = null;
  }

  createCourse() {
    if (this.courseForm.valid) {      
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
          this.courseForm.reset();
          this.showForm = false;
          this.error = null;
        }
      });
    }
  }
}
