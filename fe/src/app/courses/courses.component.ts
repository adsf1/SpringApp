import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgFor, NgIf } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { RouterLink } from '@angular/router';
import { Course } from '../models/course.model';
import { FormsModule } from '@angular/forms';
import { Author } from '../models/author.model';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink, FormsModule],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.css'
})
export class CoursesComponent implements OnInit {
  courses: Course[] = [];
  error: string | null = null;
  newCourse: { title: string; authorId: number; cost: number } = { title: '', authorId: 1, cost: 0 };
  authorIds: number[] = [];
  showForm: boolean = false;

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
    this.newCourse = { title: '', authorId: 1, cost: 0 };
    this.error = null;
  }

  // TODO: Could add validations to input
  createCourse() {
    this.http.post<Course>('http://localhost:8080/courses', this.newCourse)
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
          this.newCourse = { title: '', authorId: 1, cost: 0 };
          this.showForm = false;
          this.error = null;
        }
      });
  }
}
