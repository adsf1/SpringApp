import { Routes } from '@angular/router';
import { CoursesComponent } from './courses/courses.component';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthorComponent } from './author/author.component';

export const routes: Routes = [
    { path: 'courses', component: CoursesComponent },
    { path: 'author/:id', component: AuthorComponent },
    { path: '', component: AppComponent },
    { path: '**', component: PageNotFoundComponent }
];
