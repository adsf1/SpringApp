import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgFor, NgIf } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { RouterLink } from '@angular/router';
import { Course } from '../models/course.model';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink],
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.css'
})
export class CoursesComponent implements OnInit {
  courses: Course[] = [];
  error: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<Course[]>('http://localhost:8080/courses')
      .pipe(
        catchError(error => {
          this.error = 'Failed to get courses!';
          // console.log(error);
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
}
