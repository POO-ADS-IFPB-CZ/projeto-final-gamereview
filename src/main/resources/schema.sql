CREATE TABLE IF NOT EXISTS profile (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    game_title VARCHAR(100) NOT NULL,
    platform VARCHAR(50),
    rating DECIMAL(2,1) NOT NULL,
    text TEXT,
    review_date DATE NOT NULL,
    profile_id INT NOT NULL,

    CONSTRAINT fk_review_profile
    FOREIGN KEY (profile_id)
    REFERENCES profile(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    comment_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    profile_id INT NOT NULL,
    review_id INT NOT NULL,

    CONSTRAINT fk_comment_profile
    FOREIGN KEY (profile_id)
    REFERENCES profile(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_comment_review
    FOREIGN KEY (review_id)
    REFERENCES review(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    profile_id INT NOT NULL,
    review_id INT NOT NULL,

    CONSTRAINT fk_likes_profile
    FOREIGN KEY (profile_id)
    REFERENCES profile(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_likes_review
    FOREIGN KEY (review_id)
    REFERENCES review(id)
    ON DELETE CASCADE,

    CONSTRAINT uk_likes UNIQUE (profile_id, review_id)
);