import { Box, Container } from "@chakra-ui/react"

export const Footer = (props) => {
  return (
    <Box as="footer" role="contentinfo" bg="bg.accent.default" {...props}>
      <Container>
        <Box minH="20">Footer</Box>
      </Container>
    </Box>
  )
}