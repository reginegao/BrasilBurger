<?php

namespace App\Service;

use Symfony\Component\HttpFoundation\File\UploadedFile;

class ImageService
{
    public function upload(UploadedFile $file): string
    {
        $fileName = uniqid() . '.' . $file->guessExtension();

        $file->move(
            __DIR__ . '/../../public/uploads',
            $fileName
        );

        return $fileName;
    }
}
