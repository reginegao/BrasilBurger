<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20251229233743 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TEMPORARY TABLE __temp__burger AS SELECT id, name, description, price, image FROM burger');
        $this->addSql('DROP TABLE burger');
        $this->addSql('CREATE TABLE burger (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, menu_id INTEGER NOT NULL, name VARCHAR(255) NOT NULL, description CLOB NOT NULL, price DOUBLE PRECISION NOT NULL, image VARCHAR(255) DEFAULT NULL, CONSTRAINT FK_EFE35A0DCCD7E912 FOREIGN KEY (menu_id) REFERENCES menu (id) NOT DEFERRABLE INITIALLY IMMEDIATE)');
        $this->addSql('INSERT INTO burger (id, name, description, price, image) SELECT id, name, description, price, image FROM __temp__burger');
        $this->addSql('DROP TABLE __temp__burger');
        $this->addSql('CREATE INDEX IDX_EFE35A0DCCD7E912 ON burger (menu_id)');
        $this->addSql('CREATE TEMPORARY TABLE __temp__menu AS SELECT id, name FROM menu');
        $this->addSql('DROP TABLE menu');
        $this->addSql('CREATE TABLE menu (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(255) NOT NULL)');
        $this->addSql('INSERT INTO menu (id, name) SELECT id, name FROM __temp__menu');
        $this->addSql('DROP TABLE __temp__menu');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TEMPORARY TABLE __temp__burger AS SELECT id, name, description, price, image FROM burger');
        $this->addSql('DROP TABLE burger');
        $this->addSql('CREATE TABLE burger (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(255) NOT NULL, description CLOB NOT NULL, price DOUBLE PRECISION NOT NULL, image VARCHAR(255) DEFAULT NULL)');
        $this->addSql('INSERT INTO burger (id, name, description, price, image) SELECT id, name, description, price, image FROM __temp__burger');
        $this->addSql('DROP TABLE __temp__burger');
        $this->addSql('ALTER TABLE menu ADD COLUMN description CLOB NOT NULL');
        $this->addSql('ALTER TABLE menu ADD COLUMN price DOUBLE PRECISION NOT NULL');
    }
}
