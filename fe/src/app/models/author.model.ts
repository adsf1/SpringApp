import { Course } from './course.model';

export interface Author {
    id: number;
    name: string;
    age: number;
    courses: Course[]; 
}