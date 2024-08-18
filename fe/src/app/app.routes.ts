import { Routes } from '@angular/router';
import { CoursesComponent } from './courses/courses.component';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: 'courses', component: CoursesComponent },
    { path: '', component: AppComponent },
    { path: '**', component: PageNotFoundComponent }
];
