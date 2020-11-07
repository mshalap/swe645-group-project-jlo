import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Survey } from './survey';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
 
@Injectable({providedIn:'root'})
export class ApiService {
 
  baseURL: string = "http://184.72.100.51:31560/HW3/rest/"
 
  constructor(private http: HttpClient) {
  }
 
  getSurveys(): Observable<Survey[]> {
    console.log('getSurveys '+this.baseURL + 'survey')
    return this.http.get<Survey[]>(this.baseURL + 'survey')
  }
 
  addSurvey(survey:Survey): Observable<any> {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(survey);

    console.log(body)

    return this.http.post(this.baseURL + 'survey', body, {'headers':headers})
  }
 
}