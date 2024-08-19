import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Author } from '../models/author.model';

@Component({
  selector: 'app-author',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './author.component.html',
  styleUrl: './author.component.css'
})
export class AuthorComponent implements OnInit {
  author: Author | null = null;
  error: string | null = null;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit() {
    this.http.get<Author>(`http://localhost:8080/authors/${this.route.snapshot.paramMap.get('id')}`)
      .pipe(
        catchError(error => {
          this.error = 'Failed to get author!';
          // console.log(error);
          return of(null);
        })
      )
      .subscribe(data => {
        if (data) {
          this.author = data;
          this.error = null;
        } else {
          this.error = 'Failed to get author!';
        }
      });
  }
}
