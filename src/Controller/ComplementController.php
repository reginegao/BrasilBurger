<?php

namespace App\Controller;

use App\Entity\Complement;
use App\Form\ComplementType;
use App\Repository\ComplementRepository;
use App\Service\ImageService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/complement')]
final class ComplementController extends AbstractController
{
    #[Route(name: 'app_complement_index', methods: ['GET'])]
    public function index(ComplementRepository $complementRepository): Response
    {
        return $this->render('complement/index.html.twig', [
            'complements' => $complementRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_complement_new', methods: ['GET', 'POST'])]
    public function new(
        Request $request,
        EntityManagerInterface $entityManager,
        ImageService $imageService
    ): Response {
        $complement = new Complement();
        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();

            if ($imageFile) {
                $imageName = $imageService->upload($imageFile);
                $complement->setImage($imageName);
            }

            $entityManager->persist($complement);
            $entityManager->flush();

            return $this->redirectToRoute('app_complement_index');
        }

        return $this->render('complement/new.html.twig', [
            'complement' => $complement,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_complement_show', methods: ['GET'])]
    public function show(Complement $complement): Response
    {
        return $this->render('complement/show.html.twig', [
            'complement' => $complement,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_complement_edit', methods: ['GET', 'POST'])]
    public function edit(
        Request $request,
        Complement $complement,
        EntityManagerInterface $entityManager,
        ImageService $imageService
    ): Response {
        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();

            if ($imageFile) {
                $imageName = $imageService->upload($imageFile);
                $complement->setImage($imageName);
            }

            $entityManager->flush();

            return $this->redirectToRoute('app_complement_index');
        }

        return $this->render('complement/edit.html.twig', [
            'complement' => $complement,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_complement_delete', methods: ['POST'])]
    public function delete(
        Request $request,
        Complement $complement,
        EntityManagerInterface $entityManager
    ): Response {
        if ($this->isCsrfTokenValid('delete'.$complement->getId(), $request->request->get('_token'))) {
            $entityManager->remove($complement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_complement_index');
    }
}
