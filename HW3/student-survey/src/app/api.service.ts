import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Survey } from './survey';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
 
@Injectable({providedIn:'root'})
export class ApiService {
 
  baseURL: string = "http://localhost:3000/";  //TODO
 
  constructor(private http: HttpClient) {
  }
 
  getSurveys(): Observable<Survey[]> {
    console.log('getSurveys '+this.baseURL + 'surveys')
    return this.http.get<Survey[]>(this.baseURL + 'surveys')
  }
 
  addSurvey(survey:Survey): Observable<any> {
    const headers = { 'content-type': 'application/x-www-form-urlencoded'}  
    //const body=JSON.stringify(survey);

    var form_data = new FormData();

    for ( var key in survey ) {
        form_data.append(key, survey[key]);
    }

    console.log(form_data)
    return this.http.post(this.baseURL + 'surveys', form_data,{'headers':headers})
  }
 
}