import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import NovaPauta from './NovaPauta';
import { BrowserRouter } from 'react-router-dom';
import api from '../api/api';

vi.mock('../api/api');

describe('NovaPauta Component', () => {
  it('deve renderizar o formulário corretamente', () => {
    render(
      <BrowserRouter>
        <NovaPauta />
      </BrowserRouter>
    );
    
    expect(screen.getByText(/Nova Pauta/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Ex: Aprovação de Contas 2026/i)).toBeInTheDocument();
  });

  it('deve chamar a API ao submeter o formulário', async () => {
    vi.mocked(api.post).mockResolvedValue({ data: {} });
    
    render(
      <BrowserRouter>
        <NovaPauta />
      </BrowserRouter>
    );

    fireEvent.change(screen.getByPlaceholderText(/Ex: Aprovação de Contas 2026/i), {
      target: { value: 'Nova Assembleia' }
    });
    fireEvent.change(screen.getByPlaceholderText(/Forneça detalhes para que os associados possam decidir.../i), {
      target: { value: 'Descrição da assembleia' }
    });

    fireEvent.click(screen.getByText(/Criar Pauta/i));

    expect(api.post).toHaveBeenCalledWith('/pautas', {
      titulo: 'Nova Assembleia',
      descricao: 'Descrição da assembleia'
    });
  });
});
