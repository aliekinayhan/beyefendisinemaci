# Beyefendi Sinemacı
A full-stack film review platform where admins can publish movie reviews and users can comment and manage their watchlist.

## Live Demo
https://beyefendisinemaci.com

## Tech Stack
**Backend:** Java, Spring Boot, Spring Security, JWT, Hibernate, MySQL

**Frontend:** React, Vite

**DevOps & Cloud:** Docker, AWS EC2, AWS RDS, AWS S3, AWS CloudFront

## Features
- JWT authentication with role-based access control (Admin / User)
- Admins can add movies via TMDB API and publish video reviews (Poster photo, title and year come via TMDB)
- Admins can delete users and comments 
- Users can comment on movies and manage a personal watchlist
- Media upload and delivery via AWS S3 and CloudFront
- Rate limiting on auth and upload endpoints with Bucket4j
- Fully containerized with Docker and deployed on AWS

## Screenshots 
